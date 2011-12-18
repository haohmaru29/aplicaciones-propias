Ext.require([
    'Ext.window.*'
]);

Ext.onReady(function() {
    Repositorio.win= new Ext.Window({
        title: 'Mis repositorios',
        closable:true,
        frame: false,
        border: false,
        width:500,
        height:400,
        closeAction:'hide',
        modal:true,
        layout: 'fit',
        items: [{
            region: 'center',
            xtype: 'panel',
            items: [ Repositorio.grid ]
        }]
    });
});
