<html>
   <head>
      <title>Correlation Historical data</title>
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
               <g:actionSubmit name="correlation" action="correlation" value="Correlate"/>
            </td>
            </g:form></td>
         </tr>
      </table> 
     
     <br> 
     <br> 
     
      <g:if test="${open != null}">
      	<table border="1">
      		<tr>
      			<td>Open</td>
      			<td>${open}</td>
      		</tr>
      		<tr>
      			<td>High</td>
      			<td>${high}</td>
      		</tr>
      		<tr>
      			<td>Low</td>
      			<td>${low}</td>
      		<tr>
      			<td>Close</td>
      			<td>${close}</td>
      		</tr>
      		<tr>
      			<td>Volume</td>
      			<td>${volume}</td>
      		</tr>
      	</table>
      </g:if>
   </body>
</html>
