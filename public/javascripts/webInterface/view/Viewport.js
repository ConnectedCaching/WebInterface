Ext.define('Archer.view.Viewport', {

	extend: 'Ext.container.Container',
	alias: 'widget.viewport',

	requires: [
		'Archer.view.Viewer',
		'Archer.view.library.List',
		'Ext.layout.container.Border'
	],

	layout: 'border',

	items: [{
		region: 'center',
		xtype: 'viewer'
	}, {
		region: 'west',
		width: 225,
		xtype: 'library'
	}]

});
