#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <math.h>
#include <mpi.h>

/* Given with the fft function in the assignment */
typedef struct {float r; float i;} complex;
static complex ctmp;
#define C_SWAP(a,b) {ctmp=(a);(a)=(b);(b)=ctmp;}

const int N = 512;


int p, my_rank;
#define ROOT 0

int main (int argc, char **argv) {

   MPI_Init(&argc, &argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
   MPI_Comm_size(MPI_COMM_WORLD, &p);

   
   const char* file1 = argc == 3 ? argv[1] : "sample/1_im1";
   const char* file2 = argc == 3 ? argv[2] : "sample/1_im2";
  
   if ( my_rank==0) printf("Number of processors = %d\n",p);
   if ( my_rank==0) printf("Using the input images %s, %s\n\n",file1, file2);

   
   int matrix_read ( const char* filename, complex matrix[N][N] );
   int matrix_write ( const char* filename, complex matrix[N][N] );
   void c_fft1d(complex *r, int n, int isign);
   void matrix_print ( complex matrix[N][N], const char* matrixname, int rank );


   
   int chunk = N / p; 
   complex A[N][N], B[N][N], C[N][N];
   int i, j;
   complex tmp;
   double time_init, time_end, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14;
   MPI_Status status;
   
   matrix_read (file1, A);
   matrix_read (file2, B);

   matrix_print(A, "Matrix A initial", ROOT);
   matrix_print(B, "Matrix B initial", ROOT);
   
   if ( my_rank == ROOT )
      time_init = MPI_Wtime();
   
   if ( my_rank != ROOT )
   for (i=0;i<N;i++)
      for (j=0;j<N;j++) { A[i][j].r = 0; A[i][j].i = 0; B[i][j].r = 0; B[i][j].i = 0; C[i][j].r = 0; C[i][j].i = 0;}


   int group_size = p / 4;
   int my_grp_rank;
   int P1_array[group_size], P2_array[group_size], P3_array[group_size], P4_array[group_size];

   for(i=0; i<p; i++) {
      int processor_group = i / group_size;
      switch(processor_group){
      case 0:
         P1_array[ i%group_size ] = i;
         break;
      case 1:
         P2_array[ i%group_size ] = i;
         break;
      case 2:
         P3_array[ i%group_size ] = i;
         break;
      case 3:
         P4_array[ i%group_size ] = i;
         break;
      }
   }
   
   MPI_Group world_group, P1, P2, P3, P4; 
   MPI_Comm P1_comm, P2_comm, P3_comm, P4_comm;

  
   MPI_Comm_group(MPI_COMM_WORLD, &world_group); 

   
   int my_group = my_rank / group_size;

   if ( my_group == 0 )      { 
      
      MPI_Group_incl(world_group, p/4, P1_array, &P1);
      MPI_Comm_create( MPI_COMM_WORLD, P1, &P1_comm);
      MPI_Group_rank(P1, &my_grp_rank);
   } 
   else if ( my_group == 1 ) { 

      MPI_Group_incl(world_group, p/4, P2_array, &P2); 
      MPI_Comm_create( MPI_COMM_WORLD, P2, &P2_comm);
      MPI_Group_rank(P2, &my_grp_rank);
   } 
   else if ( my_group == 2 ) { 
      MPI_Group_incl(world_group, p/4, P3_array, &P3); 
      MPI_Comm_create( MPI_COMM_WORLD, P3, &P3_comm);
      MPI_Group_rank(P3, &my_grp_rank);
   } 
   else if ( my_group == 3 ) { 
      MPI_Group_incl(world_group, p/4, P4_array, &P4); 
      MPI_Comm_create( MPI_COMM_WORLD, P4, &P4_comm);
      MPI_Group_rank(P4, &my_grp_rank);
   } 

   chunk = N / group_size;

   if ( my_rank == ROOT ){

      for ( i=0; i<group_size; i++ ) {
         if ( P1_array[i]==ROOT ) continue;
         MPI_Send( &A[chunk*i][0], chunk*N, MPI_COMPLEX, P1_array[i], 0, MPI_COMM_WORLD );
      }
      
      for ( i=0; i<group_size; i++ ) {
         if ( P2_array[i]==ROOT ) continue;
         MPI_Send( &B[chunk*i][0], chunk*N, MPI_COMPLEX, P2_array[i], 0, MPI_COMM_WORLD );
      }
   }
   else {
      
      if ( my_group == 0 )
         MPI_Recv( &A[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, ROOT, 0, MPI_COMM_WORLD, &status );
      
      if ( my_group == 1 )
         MPI_Recv( &B[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, ROOT, 0, MPI_COMM_WORLD, &status );
   }

   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t1 = MPI_Wtime();

   if ( my_group == 0 )
      for ( i=chunk*my_grp_rank; i<chunk*(my_grp_rank+1); i++ )
         c_fft1d(A[i], N, -1);

   
   if ( my_group == 1 )
      for ( i=chunk*my_grp_rank; i<chunk*(my_grp_rank+1); i++ )
         c_fft1d(B[i], N, -1);

   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t2 = MPI_Wtime();

   if ( my_group == 0 ) {
      if ( my_grp_rank == 0 ) {
         for ( i=1; i<group_size; i++ ) {
            MPI_Recv( &A[chunk*i][0], chunk*N, MPI_COMPLEX, i, 0, P1_comm, &status );
         }
         
      }
      else 
         MPI_Send( &A[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, 0, 0, P1_comm );
   }

   if ( my_group == 1 ) {
      if ( my_grp_rank == 0 ) {
         for ( i=1; i<group_size; i++ ) {
            MPI_Recv( &B[chunk*i][0], chunk*N, MPI_COMPLEX, i, 0, P2_comm, &status );
         }
         
      }
      else 
         MPI_Send( &B[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, 0, 0, P2_comm );
   }
   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t3 = MPI_Wtime();

   if ( my_group == 0 && my_grp_rank == 0 ) {
      for (i=0;i<N;i++) {
         for (j=i;j<N;j++) {
            tmp = A[i][j];
            A[i][j] = A[j][i];
            A[j][i] = tmp;
         }
      }
   }

    
   if ( my_group == 1 && my_grp_rank == 0 ) {
      for (i=0;i<N;i++) {
         for (j=i;j<N;j++) {
            tmp = B[i][j];
            B[i][j] = B[j][i];
            B[j][i] = tmp;
         }
      }
   }
   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t4 = MPI_Wtime();

   if ( my_group == 0 ) {
      if ( my_grp_rank == 0 ) {
         for ( i=1; i<group_size; i++ ) {
            MPI_Send( &A[chunk*i][0], chunk*N, MPI_COMPLEX, i, 0, P1_comm );
         }
         
      }
      else 
         MPI_Recv( &A[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, 0, 0, P1_comm, &status );
   }
  

   if ( my_group == 1 ) {
      if ( my_grp_rank == 0 ) {
         for ( i=1; i<group_size; i++ ) {
            MPI_Send( &B[chunk*i][0], chunk*N, MPI_COMPLEX, i, 0, P2_comm );
         }
         
      }
      else 
         MPI_Recv( &B[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, 0, 0, P2_comm, &status );
   }
   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t5 = MPI_Wtime();


   if ( my_group == 0 )
      for ( i=chunk*my_grp_rank; i<chunk*(my_grp_rank+1); i++ )
         c_fft1d(A[i], N, -1);


   if ( my_group == 1 )
      for ( i=chunk*my_grp_rank; i<chunk*(my_grp_rank+1); i++ )
         c_fft1d(B[i], N, -1);

   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t6 = MPI_Wtime();

   if ( my_group == 0 )
      MPI_Send ( &A[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, P3_array[my_grp_rank], 0, MPI_COMM_WORLD );
   else if ( my_group == 1 )
      MPI_Send ( &B[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, P3_array[my_grp_rank], 0, MPI_COMM_WORLD );

   else if ( my_group == 2 ) {
      MPI_Recv( &A[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, P1_array[my_grp_rank], 0, MPI_COMM_WORLD, &status );
      MPI_Recv( &B[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, P2_array[my_grp_rank], 0, MPI_COMM_WORLD, &status );
   }

   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t7 = MPI_Wtime();

   if ( my_group == 2 ) {
      for (i= chunk*my_grp_rank ;i< chunk*(my_grp_rank+1);i++) {
         for (j=0;j<N;j++) {
            C[i][j].r = A[i][j].r*B[i][j].r - A[i][j].i*B[i][j].i;
            C[i][j].i = A[i][j].r*B[i][j].i + A[i][j].i*B[i][j].r;
         }
      }
   }

   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t8 = MPI_Wtime();

   if ( my_group == 2 ) {
      MPI_Send ( &C[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, P4_array[my_grp_rank], 0, MPI_COMM_WORLD );
   }
   else if ( my_group == 3 ) {
      MPI_Recv( &C[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, P3_array[my_grp_rank], 0, MPI_COMM_WORLD, &status );
   }
  
   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t9 = MPI_Wtime();

   if ( my_group == 3 )
      for ( i=chunk*my_grp_rank; i<chunk*(my_grp_rank+1); i++ )
         c_fft1d(C[i], N, 1);
   
   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t10 = MPI_Wtime();

   if ( my_group == 3 ) {
      if ( my_grp_rank == 0 ) {
         for ( i=1; i<group_size; i++ ) {
            MPI_Recv( &C[chunk*i][0], chunk*N, MPI_COMPLEX, i, 0, P4_comm, &status );
         }
         
      }
      else 
         MPI_Send( &C[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, 0, 0, P4_comm );
   }
   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t11 = MPI_Wtime();

   if ( my_group == 3 && my_grp_rank == 0 ) {
      for (i=0;i<N;i++) {
         for (j=i;j<N;j++) {
            tmp = C[i][j];
            C[i][j] = C[j][i];
            C[j][i] = tmp;
         }
      }
      
   }
   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t12 = MPI_Wtime();
   

   if ( my_group == 3 ) {
      if ( my_grp_rank == 0 ) {
         for ( i=1; i<group_size; i++ ) {
            MPI_Send( &C[chunk*i][0], chunk*N, MPI_COMPLEX, i, 0, P4_comm );
         }
         
      }
      else 
         MPI_Recv( &C[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, 0, 0, P4_comm, &status );
   }
   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t13 = MPI_Wtime();


   if ( my_group == 3 )
      for ( i=chunk*my_grp_rank; i<chunk*(my_grp_rank+1); i++ )
         c_fft1d(C[i], N, 1);

   MPI_Barrier(MPI_COMM_WORLD); 
   if ( my_rank == ROOT ) t14 = MPI_Wtime();
 

   if ( my_rank == ROOT ){

      for ( i=0; i<group_size; i++ ) {
         if ( P4_array[i]==ROOT ) continue; 

         MPI_Recv( &C[chunk*i][0], chunk*N, MPI_COMPLEX, P4_array[i], 0, MPI_COMM_WORLD, &status );
      }
   }
   else if ( my_group == 3 )
      MPI_Send( &C[chunk*my_grp_rank][0], chunk*N, MPI_COMPLEX, ROOT, 0, MPI_COMM_WORLD );



   MPI_Barrier(MPI_COMM_WORLD); 
   
   if ( my_rank == ROOT )
      time_end = MPI_Wtime();

   matrix_print(C, "Matrix C final", ROOT);
   if ( my_rank==0) printf("C[0][0].r     = %e\n", C[0][0].r);
   if ( my_rank==0) printf("C[N-1][N-1].r = %e\n", C[N-1][N-1].r);
  
   matrix_write("output_matrix", C);

   
   if ( my_rank==0) {

      double tcomputation = (t2-t1) + (t4-t3) + (t6-t5) + (t8-t7) + (t10-t9) + (t12-t11) + (t14-t13);
      double tcommunication = (t1-time_init) + (t3-t2) + (t5-t4) + (t7-t6) + (t9-t8) + (t11-t10) + (t13-t12) + (time_end-t14);

      printf("Total time  %f ms\n", (time_end-time_init) * 1000 );
      printf("Computation Time %f ms\n", tcomputation * 1000 );
      printf("Communication Time  %f ms\n", tcommunication * 1000 );
   }

   MPI_Finalize();
}

int matrix_read ( const char* filename, complex matrix[N][N] ) {
   if ( my_rank == ROOT ) {
      int i, j;
      FILE *fp = fopen(filename,"r");

      if ( !fp ) {
         printf("Error. This file couldn't be read because it doesn't exist: %s\n", filename);
         exit(1);
      }

      for (i=0;i<N;i++)
         for (j=0;j<N;j++) {
            fscanf(fp,"%g",&matrix[i][j].r);
            matrix[i][j].i = 0;
         }
      fclose(fp);
   }
}


int matrix_write ( const char* filename, complex matrix[N][N] ) {
   if ( my_rank == ROOT ) {
      int i, j;
      FILE *fp = fopen(filename,"w");

      for (i=0;i<N;i++) {
         for (j=0;j<N;j++)
            fprintf(fp,"   %e",matrix[i][j].r);
         fprintf(fp,"\n");
      };

      fclose(fp);
   }
}

void matrix_print ( complex matrix[N][N], const char* matrixname, int rank ) {
   if ( my_rank == rank ) {
      if ( N<33 ) {
         int i, j;
         printf("%s by process #%d\n",matrixname, rank);
         for (i=0;i<N;i++){
            for (j=0;j<N;j++) {
              printf("(%.1f,%.1f) ", matrix[i][j].r,matrix[i][j].i);
           }printf("\n");
         }printf("\n");
      }
   }
}



/*
 ------------------------------------------------------------------------
 FFT1D            c_fft1d(r,i,-1)
 Inverse FFT1D    c_fft1d(r,i,+1)
 ------------------------------------------------------------------------
*/
/* ---------- FFT 1D
   This computes an in-place complex-to-complex FFT
   r is the real and imaginary arrays of n=2^m points.
   isign = -1 gives forward transform
   isign =  1 gives inverse transform
*/

void c_fft1d(complex *r, int      n, int      isign)
{
   int     m,i,i1,j,k,i2,l,l1,l2;
   float   c1,c2,z;
   complex t, u;

   if (isign == 0) return;

   /* Do the bit reversal */
   i2 = n >> 1;
   j = 0;
   for (i=0;i<n-1;i++) {
      if (i < j)
         C_SWAP(r[i], r[j]);
      k = i2;
      while (k <= j) {
         j -= k;
         k >>= 1;
      }
      j += k;
   }

   /* m = (int) log2((double)n); */
   for (i=n,m=0; i>1; m++,i/=2);

   /* Compute the FFT */
   c1 = -1.0;
   c2 =  0.0;
   l2 =  1;
   for (l=0;l<m;l++) {
      l1   = l2;
      l2 <<= 1;
      u.r = 1.0;
      u.i = 0.0;
      for (j=0;j<l1;j++) {
         for (i=j;i<n;i+=l2) {
            i1 = i + l1;

            /* t = u * r[i1] */
            t.r = u.r * r[i1].r - u.i * r[i1].i;
            t.i = u.r * r[i1].i + u.i * r[i1].r;

            /* r[i1] = r[i] - t */
            r[i1].r = r[i].r - t.r;
            r[i1].i = r[i].i - t.i;

            /* r[i] = r[i] + t */
            r[i].r += t.r;
            r[i].i += t.i;
         }
         z =  u.r * c1 - u.i * c2;

         u.i = u.r * c2 + u.i * c1;
         u.r = z;
      }
      c2 = sqrt((1.0 - c1) / 2.0);
      if (isign == -1) /* FWD FFT */
         c2 = -c2;
      c1 = sqrt((1.0 + c1) / 2.0);
   }

   /* Scaling for inverse transform */
   if (isign == 1) {       /* IFFT*/
      for (i=0;i<n;i++) {
         r[i].r /= n;
         r[i].i /= n;
      }
   }
}
