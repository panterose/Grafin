package grails.finance

import org.apache.commons.math.stat.correlation.SpearmansCorrelation

class InstrumentController {

    static scaffold = true
		
	
	def historicalChart = {
		def instruments = Instrument.list()
		 if (params.fieldVar == null) {
			return [instruments : instruments]
		 } else {
			def instrument1 = Instrument.findBySymbol(params.select1)
			def instrument2 = Instrument.findBySymbol(params.select2)
			def firstData = getDataForInstrument(instrument1, params.fieldVar)
			def secondData = getDataForInstrument(instrument2, params.fieldVar)
			
			def data1 = [:]
			def data2 = [:]
			def xLabels = []
			
			for(def date : firstData?.keySet()){
			   if( secondData?.keySet()?.contains(date)) {
				   def shortKey = date.toString("MMM yy")
				   
				   if(!xLabels.contains(shortKey)) {
					   xLabels << shortKey
				   }
				   
				   data1[date] = Double.valueOf(firstData?.get(date))
				   data2[date] = Double.valueOf(secondData?.get(date))
			   }
			}
		   
			return [instruments : instruments,
				firstData : data1,
				secondData : data2,
				xLabels : xLabels]
		 }
	  }
	
	  def correlation = {
		  def instruments = Instrument.list()
		  if(params.select1) {
			  return [instruments : instruments,
				  open: correlate(params.select1, params.select2, 'Open'),
				  high: correlate(params.select1, params.select2, 'High'),
				  low: correlate(params.select1, params.select2, 'Low'),
				  close: correlate(params.select1, params.select2, 'Close'),
				  volume: correlate(params.select1, params.select2, 'Volume')
				  ]
		  } else {
			return [instruments : instruments]
		  }
	  }
	  
	  
	  //---- UTILS METHODS: should go to a service ----//
	  
	  def correlate(symbol1, symbol2, fieldName) {
			  def instrument1 = Instrument.findBySymbol(symbol1)
			  def instrument2 = Instrument.findBySymbol(symbol2)
			  def firstData = getDataForInstrument(instrument1, fieldName)
			  def secondData = getDataForInstrument(instrument2, fieldName)
				
				def data1 = []
				def data2 = []
				
				for(def date : firstData?.keySet()){
				   if( secondData?.keySet()?.contains(date)) {
					   data1 << Double.valueOf(firstData?.get(date))
					   data2 << Double.valueOf(secondData?.get(date))
				   }
			}
			  
			return new SpearmansCorrelation().correlation((double[])data1.toArray(),(double[])data2.toArray())
	  }
	  
	  def getDataForInstrument(instrument, field) {
		  def quotes = Quote.findAllByInstrument(instrument)
		  def data = [:]
		  for(int i = 0; i < quotes.size(); i++) {
			 data[quotes[i].date] = quotes[i][field.toLowerCase()]
		  }
		  
		  return data.sort { it.key }
	}
  
	
}
