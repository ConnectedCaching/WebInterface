Ext.define('Archer.view.geocache.Grid', {

	extend: 'Ext.grid.Panel',
	alias: 'widget.geocachegrid',

	cls: 'geocache-grid',
	disabled: false,

	requires: ['Ext.ux.PreviewPlugin', 'Ext.toolbar.Toolbar'],

	border: false,
	split: true,

	initComponent: function() {
		Ext.apply(this, {

			viewConfig: {
				plugins: [{
					pluginId: 'preview',
					ptype: 'preview',
					bodyField: 'description',
					previewExpanded: true
				}]
			},

			columns: [{
				text: 'Title',
				dataIndex: 'title',
				flex: 1,
				renderer: this.formatTitle
			}, {
				text: 'Author',
				dataIndex: 'author',
				hidden: true,
				width: 200
			}, {
				text: 'Date',
				dataIndex: 'pubDate',
				renderer: this.formatDate,
				width: 200
			}],
			dockedItems:[{
				xtype: 'toolbar',
				dock: 'top',
				items: [{
					iconCls: 'open-all',
					text: 'Open All',
					action: 'openall'
				}]
			}]
		});

		this.callParent(arguments);
	}

});
