package grails.finance

import static groovyx.net.http.ContentType.HTML
import org.joda.time.*
import org.joda.time.format.*
import org.joda.time.contrib.hibernate.*
import org.apache.http.impl.conn.*


class GoogleFeedService {
	static transactional = true
	
	def backgroundService
	
	def importHistoricals() {
		def imports = [:]
		Instrument.list().each {Instrument inst ->
			if (! Quote.findByInstrument(inst)) {
				// try to import with the exchange prefix or not: google seems to struggle otherwise
				backgroundService.execute( "Import $inst", {
					if (!importHistoricalFromInstryment(inst, false)) {
						importHistoricalFromInstryment(inst, true)
					}
				})
			}
		}
	}
	
	boolean importHistoricalFromInstryment(inst, withExchangeSymbolAdded) {
		try {
			withHttp(uri : "http://finance.google.com", contentType : HTML) {
				//---
				// set proxy using default settings
				//println getSchemeRegistry()
				ProxySelectorRoutePlanner routePlanner = new ProxySelectorRoutePlanner(
					delegate.client.getConnectionManager().getSchemeRegistry(),
					ProxySelector.getDefault());
				delegate.client.setRoutePlanner(routePlanner);
				//----
				
				def symbol = withExchangeSymbolAdded ? inst.exchange?.symbol + ":" + inst.symbol : inst.symbol
				println "import historical for [$symbol] ${inst}"
				def result = get(path:'/finance/historical', query: [q:symbol, output:'csv'])
				
				importHistoricalFromInstrAndHtml(inst, result.text())
			}
		} catch (Exception e) {
			println "Error importing $inst: ${e.message}"
			return false
		}
		return true
	}
	
	def importHistoricalFromInstrAndHtml(inst, html) {
		
		for(line in html.split('\n')) {
			if(!line.contains('Date')) {
				def fields = line.split(',')
				def dateTime = DateTimeFormat.forPattern("dd-MMM-yy").parseDateTime(fields[0])
				
				new Quote(instrument: inst,
						date: dateTime,
						open: fields[1],
						high:fields[2],
						low:fields[3],
						close:fields[4],
						volume:fields[5]).save()
			}
		}
	}
}
