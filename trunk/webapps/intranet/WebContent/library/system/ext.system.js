Namespace.reg('System.MessageBox');

System.MessageBox.denied = function(){
	Ext.MessageBox.show({
          title: System.name,
          msg: '<b>Usted no tiene permisos para usar este recurso...</b> cont&aacute;ctese con el administrador del sistema',
          buttons: Ext.MessageBox.OK,
          icon: Ext.MessageBox.INFO
  	});
};

System.MessageBox.confirm = function(message) {
    Ext.MessageBox.confirm({
        title: System.name,
        msg: message,
        icon: Ext.MessageBox.QUESTION,
        buttons: Ext.MessageBox.Yes
    });
};

System.MessageBox.info = function(message){
	Ext.MessageBox.show({
          title: System.name,
          msg: message,
          buttons: Ext.MessageBox.OK,
          icon: Ext.MessageBox.INFO
  	});
};

System.MessageBox.error = function(message){
	Ext.MessageBox.show({
         title: System.name,
         msg: message,
         buttons: Ext.MessageBox.OK,
         icon: Ext.MessageBox.ERROR
 	});
};

System.MessageBox.warning = function(message){
	Ext.MessageBox.show({
         title: System.name,
         msg: message,
         buttons: Ext.MessageBox.OK,
         icon: Ext.MessageBox.WARNING
 	});
};

System.MessageBox.question = function(message){
	Ext.MessageBox.show({
         title: System.name,
         msg: message,
         buttons: Ext.MessageBox.OK,
         icon: Ext.MessageBox.QUESTION
 	});
};