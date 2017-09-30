package BabyStats

class BabyStats {

    Integer babyHeight
    Integer babyWeightInGrams
    Integer babyHeadCircuit
    Integer babyStomachCircuit
    Date babyStatsDate
    Baby baby

    static constraints = {
        babyHeight()
        babyWeightInGrams()
        babyHeadCircuit()
        babyStomachCircuit()
        babyStatsDate()
        baby()
    }
}
