<html>
   <head>
      <title>Chart Historical data</title>
   </head>
   <body>
      <table>
         <tr>
            <g:form>
            <td>
            		<g:select name="select1" from="${instruments.symbol}" value="${params.select1}"/>
            </td> 
            <td>
            		<g:select name="select2" from="${instruments.symbol}" value="${params.select2}"/>
            	<td>
            <td>
               <g:select name="fieldVar" from="${['Open', 'High', 'Low', 'Close', 'Volume']}" value="${params.fieldVar}"/>
            </td>
            <td>
               <g:actionSubmit name="doChart" action="historicalChart" value="Chart"/>
            </td>
            </g:form></td>
         </tr>
      </table>
      
       <g:if test="${firstData != null}">
       		<% 
			   def firstMax = firstData?.values()?.max()
			   def secondMax = secondData?.values()?.max()
			   def yMax = Math.max(firstMax, secondMax)
			   
			   def priceData = [firstData?.values()?.asList(), 
				   secondData?.values()?.asList()]
			%>
	        <g:lineChart type="lc" title="End of day (${params.fieldVar}) comparison"
				size="${[600,200]}" 
				colors="${['FF0000','0000FF']}" 
				axes="x,y" 
				lineStyles="${[[2,2,2],[2,8,4]]}" 
				legend="${[ params.select1, params.select2 ]}" 
				gridLines="${100.0/11},25" 
				axesLabels="${[0:xLabels,1:[0,yMax/2,yMax]] }"
				data="${priceData}"
				dataType="simple"  />
		</g:if> 
   </body>
</html>
