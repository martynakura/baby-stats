package BabyStats

class Baby {

    String name
    String surname
    Date birthDate

    static constraints = {
        name()
        surname()
        birthDate()
    }
}
