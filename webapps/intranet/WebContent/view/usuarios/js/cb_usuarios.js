Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.define('usuarioCB', {
        extend: 'Ext.data.Model',
        fields: [ 'nombre', 'idusuario' ]
    });    
        
    Usuario.cb.storeCB = Ext.create('Ext.data.JsonStore', {
        model: 'usuarioCB',
        autoLoad: false,
        //pageSize: 15,
        proxy: {
            type: 'ajax',
            url: 'usuario/all',
            method: 'post',
            timeout: 550000,
            reader: {
                type: 'json',
                root: 'data',
                totalProperty : 'count'
            }
        },
        idProperty: 'idusuario',
        fields: ['nombre' , 'idusuario' ]
    });
    
    
    Usuario.cb.Combo = Ext.create('Ext.form.field.ComboBox', {
        fieldLabel: 'Usuario',
        displayField: 'nombre',
        valueField: 'idusuario',
        multiSelect: false,
        //renderTo: 'cb_usuarios',
        //allowBlank:true,
        editable: false,
        emptyText:'Seleccione Usuario...',
        store: Usuario.cb.storeCB,
        queryMode: 'remote',
        loadingText: 'Cargando...'
    }); 
});