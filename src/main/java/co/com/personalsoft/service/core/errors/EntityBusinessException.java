package co.com.personalsoft.service.core.errors;

public class EntityBusinessException extends Exception {

   private static final long serialVersionUID = 1L;

   public EntityBusinessException(String keyMsg) {
     super(keyMsg);
   }
}
