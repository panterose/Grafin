package grails.finance

import org.joda.time.*
import org.joda.time.contrib.hibernate.*

class Quote {
	static belongsTo = [instrument:Instrument]
	
	DateTime date
	
	Double open
	Double low
	Double high
	Double close
	Double volume
	
	String toString() {"Quote for $instrument at $date"}
}
