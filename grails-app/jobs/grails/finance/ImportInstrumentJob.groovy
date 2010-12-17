package grails.finance


class ImportInstrumentJob {
    static triggers = {
	    simple name: 'yahooFeedTrigger', startDelay: 5000, repeatInterval: 60000  
	}
	
	def yahooFeedService
    def execute() {
		println "try importing instruments ..."
        yahooFeedService.importInstruments()
    }
}
