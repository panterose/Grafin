package grails.finance


class ImportInstrumentJob {
    static triggers = {
	    simple name: 'yahooFeedTrigger', startDelay: 10000, repeatInterval: 60000  
	}
	
	def concurrent = false
	def yahooFeedService
    def execute() {
		println "try importing instruments ..."
        yahooFeedService.importInstruments()
    }
}
