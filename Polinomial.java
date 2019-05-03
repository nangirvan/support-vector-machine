public class Polinomial {
    public static void main(String[] args) {
        
//      INISIALISASI DATA, CONSTANTA, DAN LAMBDA
        int constanta = 5, lambda = 3;
        double gama = 0, threshold = 0.02, oldF = 0, newF = 0, delta = 1;
        double data[][] = new double[5][3];
        data[0] = new double[] {60, 165, 1};
        data[1] = new double[] {70, 160, 1};
        data[2] = new double[] {80, 165, 1};
        data[3] = new double[] {100, 155, -1};
        data[4] = new double[] {40, 175, -1};


//      OUTPUTTING DATA
        System.out.println("DATA");
        System.out.println("-----");
        for (int x=0; x<data.length; x++){
            for (int j=0; j<data[x].length; j++){
                System.out.printf("%.0f\t", data[x][j]);
            }
            System.out.println("");
        }
        System.out.println("");

        
//      FIND MIN AND MAX VALUE FOR NORMALIZATION
        double maxTB = 0, maxBB = 0;
        double minTB = 0, minBB = 0;
        for (int i=0; i<data.length-1; i++){
            maxTB = Math.max(data[i][0], data[i+1][0]);
            maxBB = Math.max(data[i][1], data[i+1][1]);
            minTB = Math.min(data[i][0], data[i+1][0]);
            minBB = Math.min(data[i][1], data[i+1][1]);
        }

//      INITIATE DATA WITH NORMALIZATION DATA
        for (int i=0; i<data.length; i++){
            data[i][0] = (data[i][0] - minTB) / (maxTB - minTB);
            data[i][1] = (data[i][1] - minBB) / (maxBB - minBB);
        }
        
//      COUNT KERNEL MATRIX -> K(xi, xj)
        double kernelMatrix[][] = new double[data.length][data.length];
        int iK=0;
        while (iK<data.length){
            for (int j=0; j<data.length; j++){
                kernelMatrix[iK][j] = Math.pow(((data[iK][0]*data[j][0]) + (data[iK][0]*data[j][1]) + 1), 2);
            }
            iK++;
        }

//      COUNT KERNEL K + LAMBDA^2 MATRIX
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
        
        
//      OUTPUTTING NEW DATA
        System.out.println("NEW DATA");
        System.out.println("---------");
        for (int i=0; i<data.length; i++){
            for (int j=0; j<data[i].length-1; j++){
                System.out.printf("%.3f\t", data[i][j]);
            }
            System.out.printf("%.0f", data[i][2]);
            System.out.println("");
        }
        System.out.println("");
        
//      OUTPUTTING KERNEL MATRIX -> K(xi, xj)
        System.out.println("KERNEL MATRIX");
        System.out.println("---------------");
        for (int i=0; i<kernelMatrix.length; i++){
            for (int j=0; j<kernelMatrix[i].length; j++){
                System.out.printf("%.3f\t", kernelMatrix[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");

//      OUTPUTTING KERNEL K + LAMBDA^2 MATRIX
        System.out.println("K + LAMBDA^2 MATRIX");
        System.out.println("---------------------");
        for (int i=0; i<KLambdaMatrix.length; i++){
            for (int j=0; j<KLambdaMatrix[i].length; j++){
                System.out.printf("%.3f\t", KLambdaMatrix[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");           
        
//      OUTPUTTING Y MATRIX
        System.out.println("Y MATRIX");
        System.out.println("----------");
        for (int i=0; i<YMatrix.length; i++){
            for (int j=0; j<YMatrix[i].length; j++){
                System.out.printf("%.0f\t", YMatrix[i][j]);
            }
            System.out.println("");
        }
        System.out.println(""); 
        
//      OUTPUTTING D MATRIX
        System.out.println("D MATRIX");
        System.out.println("----------");
        for (int i=0; i<DMatrix.length; i++){
            for (int j=0; j<DMatrix[i].length; j++){
                System.out.printf("%.3f\t\t", DMatrix[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
        

//      ALPHA AND F INITIALIZATION
        double totalAlpha = 0;
        double alpha[] = new double[data.length];
        for (int i=0; i<alpha.length; i++){
            alpha[i] = 0;
            totalAlpha+=alpha[i];
        }
        oldF = totalAlpha/alpha.length;
        
//      OUTPUTTING ALPHA
        System.out.print("ALPHA\t\t");
        for (int i=0; i<alpha.length; i++){
            System.out.printf("%.0f\t", alpha[i]);
        }
        System.out.println("");
        
//      DOING ITERATION TO COUNT E, DELTA, ALPHA
        int iteration = 0;
        while(iteration < 4){
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
            double jumlahAlphai = 0;
            for (int i=0; i<alpha.length; i++){
                alpha[i] += deltaA[i];
                jumlahAlphai += alpha[i];
            }
            newF = jumlahAlphai/alpha.length;
            delta = Math.abs(newF-oldF);
            oldF = newF;

            for (int i=0; i<E.length; i++){
                E[i] = 0;
            }
            
            
//          OUTPUTTING E-i
            System.out.print("E"+iteration+"\t\t");
            for (int i=0; i<E.length; i++){
                System.out.print(E[i]+"\t");
            }
            System.out.println("");
            
//          OUTPUTTING DELTA A-i
            System.out.print("Delta-a"+iteration+"\t");
            for (int i=0; i<deltaA.length; i++){
                System.out.printf("%f\t", deltaA[i]);
            }
            System.out.println("");
            
//          OUTPUTTING ALPHA-i
            System.out.print("Alpha-"+iteration+"\t\t");
            for (int i=0; i<alpha.length; i++){
                System.out.printf("%f\t", alpha[i]);
            }
            System.out.println("\n");        
            iteration++;
        }
        System.out.println("");
        
//      NEW DATA INITIALIZATION
        double inputData[] = new double[] {((45-minTB)/(maxBB-minBB)), ((176-minBB)/(maxBB-minBB)), 0};
        double newData[][] = new double[data.length][3];
        for (int i=0; i<newData.length; i++){
            for (int j=0; j<newData[i].length-1; j++){
                newData[i][j] = (inputData[j] * data[i][j]);
            }
            newData[i][2] = (alpha[i] * data[i][2]);
        }
        
//      GENERATE DECISION FOR SUPPORT VECTOR MACHINE
        double[] newKernelMatrix = new double[data.length];
        for (int i=0; i<newKernelMatrix.length; i++){
            newKernelMatrix[i] = Math.pow(((data[i][0]*inputData[0]) + (data[i][1]*inputData[1]) + 1), 2)*alpha[i];
        }
        double totalNewK = 0;
        for (int i=0; i<newKernelMatrix.length; i++){
            totalNewK += newKernelMatrix[i];
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

//      OUTPUTTING K MATRIX FOR NEW DATA
        System.out.println("KERNEL MATRIX FOR NEW DATA");
        System.out.println("---------------------------");
        for (int i=0; i<newKernelMatrix.length; i++){
            System.out.println("x"+(i+1)+"\t"+newKernelMatrix[i]+"\t");
        }
        System.out.println("\t"+totalNewK);
        System.out.println("");
        
//      OUTPUTTING RESULT
        if (totalNewK < 0){
            System.out.println("STATUS = -1");
        } else {
            System.out.println("STATUS = 1");
        }
    }
}
