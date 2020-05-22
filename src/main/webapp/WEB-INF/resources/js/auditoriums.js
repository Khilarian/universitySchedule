/**
 * 
 */

$('document').ready(function() {
	
	$('.table .btn-warning').on('click',function(event){
		
		event.preventDefault();
		
		var href= $(this).attr('href');
		
		$.get(href, function(auditorium, status){
			$('#idEdit').val(auditorium.id);
			$('#numberEdit').val(auditorium.number);
			$('#capacityEdit').val(auditorium.capacity);
			$('#buildingEdit').val(auditorium.buildingId);
		});	
		
		$('#editModal').modal();
		
	});	
});
