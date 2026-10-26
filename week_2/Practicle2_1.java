public class Practicle2_1 {
    static class Thermostat {
        private String location;
        private int temp;
        private static int Min = 16;
        private static int Max = 30;
        private static int activeCount=0;

        Thermostat(String location , int startTemp){
            this.location = location;
            if(startTemp >= Min && startTemp <= Max){
                this.temp = startTemp;
            }else{
                this.temp = 22;
            }
            activeCount++;
        }

        Thermostat(String location){
            this.location = location;
            this.temp = 22;
            activeCount++;
        }

        public void raise() {
            if (temp < Max) {
                temp++;
            } else {
                System.out.println("Already at maximum : " + Max);
            }
        }

        public void lower() {
            if (temp > Min) {
                temp--;
            } else {
                System.out.println("Already at minimum  : "+ Min);
            }
        }

        public void getTemp(){
            System.out.println(temp);
        }

        public void getCount(){
            System.out.println(activeCount);
        }
    }

    public static void main(String args[]){
        Thermostat t1 = new Thermostat("Charusat",26);
        Thermostat t2 = new Thermostat("Charusat");

        for(int i = 1 ; i <= 10 ; i++){
            t1.raise();
            t1.getTemp();
        }
        for(int i = 1 ; i <= 20 ; i++){
            t1.lower();
            t1.getTemp();
        }

        t1.getCount();
    }
}