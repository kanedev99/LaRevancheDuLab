package interfaces;

import utils.Vecteur2D;
/***
 *  Une classe abstraite qui permet de faire des rotation
 * @author Kepler Paul-Émile
 *
 */
public abstract class Rotation {

    /***
     * Méthode qui retourne la rotation approprié
     * @param vec
     * @return la rotation du vecteur
     */
    public static double Rotation(Vecteur2D vec) {
        double rotation=0;
        Vecteur2D positive=vec.vecPositif();
     
        //on s'assure de mettre les composantes en positif
       if(vec.getX()>0) {
         
           if(vec.getY()>0) {
               rotation=Math.asin(-positive.getY()/positive.getNorme());
           }else if(vec.getY()<0) {
               rotation=-Math.asin(-positive.getY()/positive.getNorme());   
           }else {
               rotation=0;
           }
       }else if(vec.getX()<0) {
          
           if(vec.getY()>0) {
               rotation= (Math.PI) -Math.asin(-positive.getY()/positive.getNorme());
           }else if(vec.getY()<0) {
               rotation=Math.PI +Math.asin(-positive.getY()/positive.getNorme());   
           }else {
               rotation= Math.PI;
           }
           
       }else if(vec.getX()==0) {
        
           if(vec.getY()>0) {
               rotation=Math.PI/2;
           }else if(vec.getY()<0){
               rotation=-Math.PI/2;   
           }else {
               rotation=0;
           }
       }
   
        return rotation;
        
    }
}
