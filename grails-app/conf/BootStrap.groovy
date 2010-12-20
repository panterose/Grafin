import grails.finance.*

class BootStrap {

    def init = { servletContext ->
		
		//System.properties.putAll( ["http.proxyHost":"nhqproxy.uk.hibm.hsbc", "http.proxyPort":"80"] )
		
		
		def nasdaq = new Exchange(name:"NASDAQ", symbol:"NASDAQ", country:"US").save(flush:true)
		def dj = new Exchange(name:"Dow Jones", symbol:"NYSE", country:"US").save(flush:true)
		def paris = new Exchange(name:"Bourse de Paris", symbol:"EPA", country:"FR").save(flush:true)
		def lse = new Exchange(name:"London Stock Exchange", symbol:"LON", country:"UK").save(flush:true)
		def sp = new Exchange(name:"S&P", symbol:"NYSE", country:"US").save(flush:true)
		
		
		//def ndx = new Index(name:"NASDAQ-100", symbol:"NDX", exchange:nasdaq).save(flush:true)
		def dji = new Index(name:"Dow Jones Industrial Average", symbol:"DJI",  exchange:dj).save(flush:true)
		//def cac = new Index(name:"CAC 40", symbol:"FCHI", exchange:paris).save(flush:true)
		//def ftse = new Index(name:"FTSE 100",symbol:"FTSE", exchange:lse).save(flush:true)
		//def sp500 = new Index(name:"S&P 500",symbol:"GSPC", exchange:sp).save(flush:true)
				
		def google = new Datasource(name:"Google Finance", url:"http://finance.google.com").save(flush:true)
		def yahoo = new Datasource(name:"Yahoo Finance", url:"http://query.yahooapis.com/v1/public/").save(flush:true)
		
    }
    def destroy = {
    }
}
