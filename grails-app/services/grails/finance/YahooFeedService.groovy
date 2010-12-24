package grails.finance

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.TEXT
import org.apache.http.impl.conn.*

class YahooFeedService {

    static transactional = true

    def importInstruments() {
		//def ds = Datasource.FindByName("Yahoo Finance");
		def Indices = Index.list()
		Indices.each { idx ->
			if (! Instrument.findByIndex(idx)) {
				println "import instruments for $idx"
				importAllInstrumentsFromIndex(idx)
			}
		}
    }
	
	def importAllInstrumentsFromIndex(idx) {
		//System.properties.putAll( ["http.proxyHost":"nhqproxy.uk.hibm.hsbc", "http.proxyPort":"80"] )
		withHttp(uri: "http://finance.yahoo.com") {
			//--- 
			// set proxy using default settings
			//println getSchemeRegistry()
			ProxySelectorRoutePlanner routePlanner = new ProxySelectorRoutePlanner(
				delegate.client.getConnectionManager().getSchemeRegistry(),
				ProxySelector.getDefault());
			delegate.client.setRoutePlanner(routePlanner);
			//----
			
			
			def html = get(path : '/q/cp', query : [s:"^${idx.symbol}+Components"])
			
			//assert html.HEAD.size() == 1
			//assert html.BODY.size() == 1
			
			 def trs = html.'**'.findAll {it.@class == 'yfnc_tabledata1'}.collect{it.parent()} as Set
			 def symbols  = trs.collect {[symbol:it.children()[0].text(), name:it.children()[1].text()]}.findAll{it.symbol}

			 
			 symbols.each {
				 new Instrument(exchange:idx.exchange, index:idx, name: it.name, symbol:it.symbol.split("\\.")[0], type: "Stock").save()
			 }
			 
		 }
	}
}
