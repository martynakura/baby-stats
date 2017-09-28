package BabyStats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(BabyStatsController)
@Mock(BabyStats)
class BabyStatsControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        params["height"] = 62
        params["weight"] = 6.2
        params["headCircuit"] = 36
        params["stomachCircuit"] = 36
        params["notes"] = "Test"
        params["statsDate"] = new Date(3017, 10, 28)
        //assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
        controller.index()

        then:"The model is correct"
        !model.babyStatsList
        model.babyStatsCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
        controller.create()

        then:"The model is correctly created"
        model.babyStats!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def babyStats = new BabyStats()
        babyStats.validate()
        controller.save(babyStats)

        then:"The create view is rendered again with the correct model"
        model.babyStats!= null
        view == 'create'

        when:"The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        babyStats = new BabyStats(params)

        controller.save(babyStats)

        then:"A redirect is issued to the show action"
        response.redirectedUrl == '/babyStats/show/1'
        controller.flash.message != null
        BabyStats.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
        controller.show(null)

        then:"A 404 error is returned"
        response.status == 404

        when:"A domain instance is passed to the show action"
        populateValidParams(params)
        def babyStats = new BabyStats(params)
        controller.show(babyStats)

        then:"A model is populated containing the domain instance"
        model.babyStats == babyStats
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
        controller.edit(null)

        then:"A 404 error is returned"
        response.status == 404

        when:"A domain instance is passed to the edit action"
        populateValidParams(params)
        def babyStats = new BabyStats(params)
        controller.edit(babyStats)

        then:"A model is populated containing the domain instance"
        model.babyStats == babyStats
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(null)

        then:"A 404 error is returned"
        response.redirectedUrl == '/babyStats/index'
        flash.message != null

        when:"An invalid domain instance is passed to the update action"
        response.reset()
        def babyStats = new BabyStats()
        babyStats.validate()
        controller.update(babyStats)

        then:"The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.babyStats == babyStats

        when:"A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        babyStats = new BabyStats(params).save(flush: true)
        controller.update(babyStats)

        then:"A redirect is issued to the show action"
        babyStats != null
        response.redirectedUrl == "/babyStats/show/$babyStats.id"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then:"A 404 is returned"
        response.redirectedUrl == '/babyStats/index'
        flash.message != null

        when:"A domain instance is created"
        response.reset()
        populateValidParams(params)
        def babyStats = new BabyStats(params).save(flush: true)

        then:"It exists"
        BabyStats.count() == 1

        when:"The domain instance is passed to the delete action"
        controller.delete(babyStats)

        then:"The instance is deleted"
        BabyStats.count() == 0
        response.redirectedUrl == '/babyStats/index'
        flash.message != null
    }
}
