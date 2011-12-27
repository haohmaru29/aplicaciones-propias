Ext.require([
    'Ext.data.*',
    'Ext.grid.*',
    'Ext.tree.*'
]);
Ext.onReady(function() {
	Ext.define('tree.folders', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'idusuario',     type: 'string'},
            {name: 'clave',     type: 'string'},
            {name: 'correo', type: 'string'}
        ]
    });

	Correo.tree.store = Ext.create('Ext.data.TreeStore', {
        //model: 'tree.folders',
        proxy: {
            type: 'ajax',
            url: 'correo/folder'
        },
        root: {
        	text: 'Ext JS',
            id: 'src',
            root: 'data',
            expanded: true
        },
        sorters: [{
            property: 'leaf',
            direction: 'ASC'
        }, {
            property: 'text',
            direction: 'ASC'
        }]
    });

	Correo.tree.render = Ext.create('Ext.tree.Panel', {
        id: 'tree',
        store: Correo.tree.store,
        width: 250,
        height: 300,
        viewConfig: {
            plugins: {
                ptype: 'treeviewdragdrop',
                appendOnly: true
            }
        },
        renderTo: 'mis_bandejas'
    });
	
});