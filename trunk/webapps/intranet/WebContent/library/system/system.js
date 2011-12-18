
System.print = function() {
    window.print();	
};
	
System.login = function(){
    if( Login.flag ){
        Login.form._submit(function(data){
            if( data.success) location.href = '?tpl=home';
            else alert( 'false -> ' + data.value);
        });
    } else {
        alert('Hay errores!!!');
    }
		
};
	
System.closeSession = function(){
    var sessionMask = new Ext.LoadMask(Ext.getBody()
        , {
            msg:"<b>Cerrando Sesi&oacute;n...</b> Espere por favor...", 
            removeMask:true
        });
		
    sessionMask.show();
    Ext.Ajax.request({
        url:'security/closeSession'
        ,
        method:'post'
        ,
        callback: function(){
            location.href = '';
        }
    });
};
	
System.sessionClose = function(){
    Ajax.request('security/closeSession', null, function(){
        location.href = '';
    });
};
	
System.pdf = function(content){
    document.document_pdf.content.value = content ;
    document.document_pdf.submit();  
};
	
System.maskMessage = "Cargando Datos...<b>Espere Por Favor</b>";
	
System.changePhoto = function(e, img){
    var imgNode = e.firstChild;
    imgNode.src = img;
};
	
System.changeView = function(arr, view){
    $.each( arr, function(i, item){
        if( view == item ) $('#' + view).css('display', '');
        else $('#' + item).css('display', 'none');
    });
};
	
System.parseObject = function(obj, partId){
    $.each(obj, function(i, data){
        if( data != null){
            if( typeof(data) == 'object') System.parseObject(data, partId);	
            else $(partId + i).html(data);
        }
    });
		
    return true;
};
	
System.serializeJson = function(obj){
    var htmlVars = '';
    $.each(obj, function(key, value){
        htmlVars += '&' + key + '=' + value;
    });
		
    return htmlVars;
};
	
System.errorMessage = function(msg){
    var message = msg;
		
    if( msg == '')
        message = 'Error en la operacion!';
		
    return message;
};
		
	