package BabyStats

class BabyStats {

    Integer height
    Double weight
    Integer headCircuit
    Integer stomachCircuit
    String notes
    Date statsDate

    static constraints = {
        height()
        weight()
        headCircuit()
        stomachCircuit()
        notes()
        statsDate()
    }
}
