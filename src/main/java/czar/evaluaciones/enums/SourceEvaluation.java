package czar.evaluaciones.enums;

public enum SourceEvaluation {
    EXAM("Examen"), CATEGORIES("Categor\u00EDas");
    
    private String description;
    
    private SourceEvaluation(String pDesc) {
        this.description = pDesc;
    }
    
    public String getDescription() {
        return this.description;
    }
}
