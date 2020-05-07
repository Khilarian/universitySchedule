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
			$('#buildingEdit').val(auditorium.building.id);
		});	
		
		$('#editModal').modal();
		
	});
	
	$('.table .btn-danger').on('click',function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$('#deleteModal #delRef').attr('href', href);
		$('#deleteModal').modal();
		
	});
	
});