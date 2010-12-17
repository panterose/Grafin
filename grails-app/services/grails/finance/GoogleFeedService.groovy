package grails.finance

import static groovyx.net.http.ContentType.HTML
import org.joda.time.*
import org.joda.time.format.*
import org.joda.time.contrib.hibernate.*

class GoogleFeedService {
	static transactional = true

	def importHistoricals() {
		def imports = [:]
		Instrument.list().each {Instrument inst ->
			if (! Quote.findByInstrument(inst)) {
				try {
					importHistoricalFromInstryment(inst)
				} catch (Exception e) {e.printStackTrace()}
			}
		}
	}

	def importHistoricalFromInstryment(inst) {
		
		withAsyncHttp(poolSize : 4, uri : "http://finance.google.com", contentType : HTML) {
			def result = get(path:'/finance/historical', query: [q:inst.symbol, output:'csv']) { resp, html -> 
				println ' got async response!'
				return html
			}
			
			assert result instanceof java.util.concurrent.Future
			
			 while (! result.done) {
				Thread.sleep(2000)
			 }

			importHistoricalFromInstrAndHtml(inst, result.get().toString())
		}
	}
	
	def importHistoricalFromInstrAndHtml(inst, html) {
		println "import historical for ${inst}"
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
