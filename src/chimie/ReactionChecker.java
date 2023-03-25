package chimie;
import chimie.MolType;
import chimie.ReactionDat;
/**
Classe permettant de faire la réaction théorique entre deux types de molécules
@author Brian Normant
@author Kepler Paul-Émile
@since sprint1
**/
public class ReactionChecker {
    /** liste des réactions possibles */
    private static ReactionDat REACTIONS[] = new ReactionDat[] {
       new ReactionDat(MolType.ACIDE, MolType.BASE, 
          MolType.EAU, MolType.SEL),
       new ReactionDat(MolType.COMBUSTIBLE, MolType.COMBURANT, 
          MolType.EAU, MolType.BASE),
    };

    /** liste des réactions de destruction*/
    private static ReactionDat RDestruction[]=new ReactionDat[]{
        new ReactionDat(MolType.ACIDE,MolType.BASE),new ReactionDat(MolType.COMBUSTIBLE,MolType.EAU),
        new ReactionDat(MolType.OXYDANT,MolType.REDUCTEUR), new ReactionDat(MolType.EAU,MolType.COMBURANT) ,
        new ReactionDat(MolType.EAU,MolType.SEL),new ReactionDat(MolType.METAL,MolType.OXYDANT),  
        new ReactionDat(MolType.NOBLE,MolType.ACIDE),new ReactionDat(MolType.NOBLE,MolType.BASE),
        new ReactionDat(MolType.NOBLE,MolType.OXYDANT),new ReactionDat(MolType.NOBLE,MolType.REDUCTEUR),
        new ReactionDat(MolType.NOBLE,MolType.EAU),new ReactionDat(MolType.NOBLE,MolType.SEL),
        new ReactionDat(MolType.NOBLE,MolType.METAL),new ReactionDat(MolType.NOBLE,MolType.COMBURANT),
        new ReactionDat(MolType.NOBLE,MolType.COMBUSTIBLE)
    };

    /** liste des réactions d'amplication*/
    private static ReactionDat RAmplification[]=new ReactionDat[] {
         //    Acide et eau, Base et eau, Combustible et Comburant , Metal et Eau, Sel et Acide, Reducteur et Metal
        new ReactionDat(MolType.ACIDE,MolType.EAU), new ReactionDat(MolType.BASE,MolType.EAU),
        new ReactionDat(MolType.COMBUSTIBLE,MolType.COMBURANT), new ReactionDat(MolType.METAL,MolType.EAU),
        new ReactionDat(MolType.SEL,MolType.ACIDE),new ReactionDat(MolType.REDUCTEUR,MolType.METAL)
     };
   
   /**
   Méthode qui renvoie les produits crées par une réaction
   @param R1 Réactif 1
   @param R1 Réactif 2
   @return les produits associés à cette réaction
   **/
   //Brian Normant
   public static MolType[] forIn(MolType R1, MolType R2) {
      MolType result[] = new MolType[0];
      var reaction = new ReactionDat(R1, R2);
      for (var possible : REACTIONS) {
         if (possible.equals(reaction)) {
            if (possible.singleProduct()) {
               return new MolType[] {
                  possible.getP1()};
            } else return new MolType[] {
                  possible.getP1(),
                  possible.getP2()
            };
         }
      }
      return result;
   }
   
   /**
    * Méthode qui détermine si les réactifs forme une réaction de destruction
    * @param R1 Réactif 1
    * @param R1 Réactif 2
    * @return si il y a une réaction de destruction
    */
    //Kepler Paul-Émile
   public static boolean destruction(MolType R1,MolType R2) {
       boolean destruction=false;
      ReactionDat verif= new ReactionDat(R1,R2);
       for(int i=0;i<RDestruction.length-1;i++) {
               if(verif.equals(RDestruction[i])) {
                      destruction=true;   
                   };
       }
       
       return destruction;
       
   }//Fin Methode
   
   /**
    * Méthode qui détermine si les réactifs forme une réaction d'amplication
    * @param R1 Réactif 1
    * @param R1 Réactif 2
    * @return si il y a une réaction d'amplication
    */
    //Kepler Paul-Émile
    public static boolean amplification(MolType R1,MolType R2) {
       boolean amplification=false;
      ReactionDat verif= new ReactionDat(R1,R2);
       for(int i=0;i<RAmplification.length-1;i++) {
               if(verif.equals(RAmplification[i])) {
                      amplification=true;   
                   };
       }
       
       return amplification;
       
   }//Fin methode
   
}
