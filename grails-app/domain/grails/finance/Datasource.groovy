package grails.finance

class Datasource {
	static constraints = {
		name(name:true, blank:false)
		url(url:true, blank:false)
	}
	String name
	String url
	
	String toString() {name}
}
