Ext.define('Archer.view.Viewport', {

	extend: 'Ext.container.Viewport',

	requires: [
		'Archer.view.Viewer',
		'Archer.view.list.List',
		'Ext.layout.container.Border'
	],

	layout: 'border',

	items: [{
		region: 'center',
		xtype: 'viewer'
	}, {
		region: 'west',
		width: 225,
		xtype: 'list'
	}]

});
