/**
 * 
 */

$('document').ready(function() {
	
	$('.table .btn-warning').on('click',function(event){
		
		event.preventDefault();
		
		var href= $(this).attr('href');
		
		$.get(href, function(building, status){
			$('#idEdit').val(building.id);
			$('#nameEdit').val(building.name);
			$('#addressEdit').val(building.address);
		});	
		
		$('#editModal').modal();
		
	});	
});
