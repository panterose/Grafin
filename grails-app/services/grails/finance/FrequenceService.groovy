package grails.finance


class FrequenceService {
	def NBINS = 30
	
    static transactional = true

    def frequence(input) {
		def step = calcStep(input)
		def bins = makeBins(input.min(), step) 
		def frequency = [:]
		
		for( int i in 0..<NBINS) {
			frequency[ bins[i].label ] = 0
		}
		
		def size = input.size()
		
		input.each { 
			def index = calcIndex(it, bins)
			frequency[ bins[index].label]++
		}	
		
		return frequency
    }

	def makeBins(min, step) {
		def bins = []
		
		for( int i in 0..<NBINS) {
			bins << new Bin(min + i * step, min + (i + 1) * step)
		}
		
		return bins
    }
	
	def reduceKeys(frequency) {
		def reduced = [:]
		
		frequency.each { 
			key,value -> 
				reduced[Math.round(Double.valueOf(key)/(1000 * 1000))] = value
		}
		
		return reduced
	}
	
	def calcIndex(it, bins) {
		for(int i in 0..<bins.size()) {
			if(it >= bins[i].start && it < bins[i].end) {
				return i
			}
		}
		
		return bins.size() - 1 
	}
	
	def calcStep(input) {
		return (input.max() - input.min())/ NBINS
	}
}
