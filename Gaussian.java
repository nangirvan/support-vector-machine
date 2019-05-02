public class Gaussian {
    public static void main(String[] args) {
//      DATA AND PARAMETER INITIALIZATION
        int constanta = 5, lambda = 3;
        double sigma = 0.5, gama = 0, threshold = 0.02, oldF = 0, newF = 0, delta = 1;
        double data[][] = new double[5][3];
        data[0] = new double[] {60, 165, 1};
        data[1] = new double[] {70, 160, 1};
        data[2] = new double[] {80, 165, 1};
        data[3] = new double[] {100, 155, -1};
        data[4] = new double[] {40, 175, -1};


//      COUNT KERNEL MATRIX  
        double kernelMatrix[][] = new double[data.length][data.length];
        int iKernel = 0;
        while (iKernel < data.length){
            for (int i=0; i<data.length; i++){
                kernelMatrix[iKernel][i] = Math.exp(Math.pow(-Math.sqrt(Math.pow((data[i][0] - data[iKernel][0]), 2) + Math.pow((data[i][1] - data[iKernel][1]), 2)), 2) / 2 * Math.pow((sigma), 2));
            }
            iKernel++;
        }

//      COUNT MATRIX OF KERNEL K + LAMBDA^2
        double KLambdaMatrix[][] = new double[data.length][data.length];
        for (int i=0; i<KLambdaMatrix.length; i++){
            for (int j=0; j<KLambdaMatrix[i].length; j++){
                KLambdaMatrix[i][j] = kernelMatrix[i][j] + Math.pow(lambda, 2);
            }
        }

//      COUNT Y MATRIX
        double YMatrix[][] = new double[data.length][data.length];
        int iY=0;
        while (iY<data.length){
            for (int i=0; i<data.length; i++){
                YMatrix[iY][i] = data[iY][2] * data[i][2];
            }
            iY++;
        }

//      COUNT D MATRIX
        double DMatrix[][] = new double[data.length][data.length];
        int iD=0;
        for (int i=0; i<data.length; i++){
            for (int j=0; j<data.length; j++){
                DMatrix[i][j] = KLambdaMatrix[i][j] * YMatrix[i][j];
            }
        }
        
//      COUNT GAMA
        double maxDij = 0;
        for (int i=0; i<DMatrix.length; i++){
            for (int j=0; j<DMatrix[i].length; j++){
                if (DMatrix[i][j] > maxDij){
                    maxDij = DMatrix[i][j];
                }
            }
        }
        gama = (2/maxDij)/2;
        
//      OUTPUTTING DATA
        System.out.println("DATA");
        System.out.println("-----");
        for (int i=0; i<data.length; i++){
            for (int j=0; j<data[i].length; j++){
                System.out.printf("%.0f\t", data[i][j]);
            }
            System.out.println("");
        }
        System.out.println("\n");
        
//      OUTPUTTING KERNEL MATRIX
        System.out.println("KERNEL MATRIX");
        System.out.println("--------------");
        for (int i=0; i<kernelMatrix.length; i++){
            for (int j=0; j<kernelMatrix[i].length; j++){
                System.out.print(kernelMatrix[i][j]+"\t\t");
            }
            System.out.println("");
        }
        System.out.println("\n");
        
//      OUTPUTTING KERNEL K + LAMBDA^2 MATRIX
        System.out.println("K + LAMBDA^2 MATRIX");
        System.out.println("--------------------");
        for (int i=0; i<KLambdaMatrix.length; i++){
            for (int j=0; j<KLambdaMatrix[i].length; j++){
                System.out.print(KLambdaMatrix[i][j]+"\t\t");
            }
            System.out.println("");
        }
        System.out.println("\n");           
        
//      OUTPUT Y MATRIX
        System.out.println("Y MATRIX");
        System.out.println("---------");
        for (int i=0; i<YMatrix.length; i++){
            for (int j=0; j<YMatrix[i].length; j++){
                System.out.printf("%.0f\t", YMatrix[i][j]);
            }
            System.out.println("");
        }
        System.out.println("\n"); 
        
//      OUTPUT D MATRIX
        System.out.println("D MATRIX");
        System.out.println("----------");
        for (int i=0; i<DMatrix.length; i++){
            for (int j=0; j<DMatrix[i].length; j++){
                System.out.print(DMatrix[i][j]+"\t\t");
            }
            System.out.println("");
        }
        System.out.println("\n");


//      ALPHA AND F INITIALIZATION
        double totalAlpha = 0;
        double alpha[] = new double[data.length];
        for (int i=0; i<alpha.length; i++){
            alpha[i] = 0;
            totalAlpha+=alpha[i];
        }
        oldF = totalAlpha/alpha.length;
        
//      OUTPUTTING ALPHA ARRAY
        System.out.print("ALPHA\t\t");
        for (int i=0; i<alpha.length; i++){
            System.out.printf("%.0f\t", alpha[i]);
        }
        System.out.println("");

//      DOING ITERATION TO COUNT E, DELTA A, ALPHA
        int iteration = 0;
        while(iteration < 7){
//          COUNT E-i
            double E[] = new double[data.length];
            for (int i=0; i<E.length; i++){
                for (int j=0; j<E.length; j++){
                    E[i] += (DMatrix[j][i]*alpha[j]);
                }
            }

//          COUNT DELTA A-i
            double deltaA[] = new double[data.length];
            for (int i=0; i<deltaA.length; i++){
                deltaA[i] = Math.min(Math.max((gama*(1-E[i])), -alpha[i]), (constanta-alpha[i]));
            }

//          COUNT ALPHA-i
            double totalAlphai = 0;
            for (int i=0; i<alpha.length; i++){
                alpha[i] += deltaA[i];
                totalAlphai += alpha[i];
            }
            newF = totalAlphai/alpha.length;
            delta = Math.abs(newF-oldF);
            oldF = newF;
            
//          OUTPUTTING E-i
            System.out.print("E"+iteration+"\t\t");
            for (int i=0; i<E.length; i++){
                System.out.print(E[i]+"\t\t");
            }
            System.out.println("");
            
//          OUTPUTTING DELTA A-i
            System.out.print("Delta-a"+iteration+"\t");
            for (int i=0; i<deltaA.length; i++){
                System.out.print(deltaA[i]+"\t\t\t");
            }
            System.out.println("");
            
//          OUTPUTTING ALPHA-i
            System.out.print("Alpha-"+iteration+"\t\t");
            for (int i=0; i<alpha.length; i++){
                System.out.printf(alpha[i]+"\t\t\t");
            }
            System.out.println("\n");        
            iteration++;
        }
        
//      NEW DATA INITIALIZATION
        double inputData[] = new double[] {45, 176};
        double newData[][] = new double[data.length][3];
        for (int i=0; i<newData.length; i++){
            for (int j=0; j<newData[i].length-1; j++){
                newData[i][j] = (inputData[j] * data[i][j]);
            }
            newData[i][2] = (alpha[i] * data[i][2]);
        }

//      GENERATING DECISION FOR SUPPORT VECTOR MACHINE
        double[] newKernelMatrix = new double[data.length];
        for (int i=0; i<newKernelMatrix.length; i++){
            newKernelMatrix[i] = Math.exp(Math.pow(-Math.sqrt(Math.pow((data[i][0] - data[i][0]), 2) + Math.pow((data[i][1] - data[i][1]), 2)), 2) / 2 * Math.pow((sigma), 2)) * newData[i][2];
        }
        double totalNewKernel = 0;
        for (int i=0; i<newKernelMatrix.length; i++){
            totalNewKernel += newKernelMatrix[i];
        }

//      OUTPUTTING INPUT DATA
        System.out.println("INPUT DATA");
        System.out.println("-----------");
        System.out.println(inputData[0]+"\t"+inputData[1]);
        System.out.println("\n");

//      OUTPUTTING NEW DATA        
        System.out.println("NEW DATA");
        System.out.println("---------");
        for (int i=0; i<newData.length; i++){
            for (int j=0; j<newData[i].length; j++){
                System.out.printf("%.3f\t",newData[i][j]);
            }
            System.out.println("");
        }
        System.out.println("\n");

//      OUTPUTTING KERNEL MATRIX FOR NEW DATA
        System.out.println("KERNEL MATRIX FOR NEW DATA");
        System.out.println("---------------------------");
        for (int i=0; i<newKernelMatrix.length; i++){
            System.out.println("x"+(i+1)+"\t"+newKernelMatrix[i]+"\t");
        }
        System.out.println("\t"+totalNewKernel);
        System.out.println("");
        
//      OUTPUTTING RESULT
        if (totalNewKernel < 0){
            System.out.println("STATUS = -1");
        } else {
            System.out.println("STATUS = 1");
        }        
    }
}
