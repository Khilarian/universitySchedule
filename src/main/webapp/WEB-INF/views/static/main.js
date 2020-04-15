/**
 * 
 */

$('document').ready(function(){
	
	$('.table .btn').on('click',function(event){
		
		event.preventDefault();
		
		var href= $(this).attr('href');
		
		$.get(href, function(building, status){
			$('#idEdit').val(building.id);
			$('#nameEdit').val(building.name);
			$('#addressEdit').val(building.address);
		});
		
		$('#editModal').modal();
		
	});
	
	$('.table #deleteButton').on('click',function(event){
		event.preventDefault();
		var href= $(this).attr('href');
		$('#deleteModal #delRef').attr('href',href);
		$('#deleteModal').modal();
		
	});
	
});