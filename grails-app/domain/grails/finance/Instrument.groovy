package grails.finance;

class Instrument {
	static hasMany = [quotes: Quote]
	
	static constraints = {
		name(maxSize:100)
		symbol(unique:true, maxSize:25, blank:false)
		type(inList:["Stock", "Index", "Currency"])
	}
	
	String name
	String symbol
	String type
	Exchange exchange
	Index index
	
	String toString() {name}
	
}
