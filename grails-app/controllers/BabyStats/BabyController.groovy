package BabyStats

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BabyController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Baby.list(params), model:[babyCount: Baby.count()]
    }

    def show(Baby baby) {
        respond baby
    }

    def create() {
        respond new Baby(params)
    }

    @Transactional
    def save(Baby baby) {
        if (baby == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (baby.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond baby.errors, view:'create'
            return
        }

        baby.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'baby.label', default: 'Baby'), baby.id])
                redirect baby
            }
            '*' { respond baby, [status: CREATED] }
        }
    }

    def edit(Baby baby) {
        respond baby
    }

    @Transactional
    def update(Baby baby) {
        if (baby == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (baby.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond baby.errors, view:'edit'
            return
        }

        baby.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'baby.label', default: 'Baby'), baby.id])
                redirect baby
            }
            '*'{ respond baby, [status: OK] }
        }
    }

    @Transactional
    def delete(Baby baby) {

        if (baby == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        baby.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'baby.label', default: 'Baby'), baby.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'baby.label', default: 'Baby'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
