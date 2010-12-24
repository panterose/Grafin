package grails.finance


import org.joda.time.DateTimeConstants

class HistoricalQueryService {

    static transactional = true

    def queryVals(symbol,fieldName) {
		def instrument = Instrument.findBySymbol(symbol)
		def quotes = Quote.findByInstrument(instrument, [sort:'date'])
		
		Double[] data = []
		quotes.each {data << it[fieldName]?.toDouble()}
		return data
    }

    def queryWeeklyVals(symbol,fieldName) {
		def instrument = Instrument.findBySymbol(symbol)
		def quotes = Quote.findByInstrument(instrument, [sort:'date'])
		
		Double[] data = []
		quotes
			.findAll {it.date.getDayOfWeek() == DateTimeConstants.FRIDAY}
			.each {data << it[fieldName]?.toDouble()}
		
		return data
    }
	
    def queryPairVals(symbol1, symbol2, fieldName) {
		def instrument1 = Instrument.findBySymbol(symbol1)
		def instrument2 = Instrument.findBySymbol(symbol2)
		
		def quotes = [:].withDefault{ [q1:0, q2:0.0]}
		Quote.findByInstrument(instrument1, [sort:'date']).each { quotes[it.date].q1 = it[fieldName]?.toDouble() }
		Quote.findByInstrument(instrument2, [sort:'date']).each { quotes[it.date].q2 = it[fieldName]?.toDouble() }
		
		def data = []
		
		quotes.each {
			if (it.value.q1 && it.value.q2) {	
				data << it.value.q2 - it.value.q1
			}
		}
		
		return data
    }
    
    def pairedValuesMap(symbol, symbol2, fieldName) {
		def data1 = []
		def data2 = []
		
		def instrument1 = Instrument.findBySymbol(symbol1)
		def instrument2 = Instrument.findBySymbol(symbol2)
		
		def quotes = [:].withDefault{ [q1:0, q2:0.0]}
		Quote.findByInstrument(instrument1, [sort:'date']).each { quotes[it.date].q1 = it[fieldName]?.toDouble() }
		Quote.findByInstrument(instrument2, [sort:'date']).each { quotes[it.date].q2 = it[fieldName]?.toDouble() }
		
		quotes.each {
			if (it.value.q1 && it.value.q2) {	
				data1 << it.value.q1
				data2 << it.value.q2
			}
		}
		
		
		def map = [:]
        map["data1"] = data1
        map["data2"] = data2
		
		return map
    }
}
