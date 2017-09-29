package BabyStats

class BabyStats {

    Integer height
    Double weight
    Integer headCircuit
    Integer stomachCircuit
    Date statsDate

    static constraints = {
        height()
        weight()
        headCircuit()
        stomachCircuit()
        statsDate()
    }
}
