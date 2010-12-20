package grails.finance

class ImportHistoricalJob {
	static triggers = {
		simple name: 'googleFeedTrigger', startDelay: 30000, repeatInterval: 60000
	}
	
	def concurrent = false
	def googleFeedService
	def execute() {
		println "try importing historicals ..."
		googleFeedService.importHistoricals()
	}
}
