package BabyStats

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BabyStatsController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BabyStats.list(params), model:[babyStatsCount: BabyStats.count()]
    }

    def show(BabyStats babyStats) {
        respond babyStats
    }

    def create() {
        respond new BabyStats(params)
    }

    @Transactional
    def save(BabyStats babyStats) {
        if (babyStats == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (babyStats.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond babyStats.errors, view:'create'
            return
        }

        babyStats.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'babyStats.label', default: 'Baby Stats'), babyStats.id])
                redirect babyStats
            }
            '*' { respond babyStats, [status: CREATED] }
        }
    }

    def edit(BabyStats babyStats) {
        respond babyStats
    }

    @Transactional
    def update(BabyStats babyStats) {
        if (babyStats == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (babyStats.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond babyStats.errors, view:'edit'
            return
        }

        babyStats.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'babyStats.label', default: 'Baby Stats'), babyStats.id])
                redirect babyStats
            }
            '*'{ respond babyStats, [status: OK] }
        }
    }

    @Transactional
    def delete(BabyStats babyStats) {

        if (babyStats == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        babyStats.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'babyStats.label', default: 'Baby Stats'), babyStats.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'babyStats.label', default: 'Baby Stats'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
