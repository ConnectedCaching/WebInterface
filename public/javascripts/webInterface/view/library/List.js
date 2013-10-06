Ext.define('Archer.view.library.List', {

	extend: 'Ext.panel.Panel',
	alias: 'widget.library',

	requires: ['Ext.toolbar.Toolbar'],

	title: 'My Library',
	collapsible: true,
	animCollapse: false,
	floatable: false,
	margins: '8 0 8 8',
	layout: 'fit',
	split: true,
	border: false,

	initComponent: function() {

		Ext.apply(this, {
			items: [{
				xtype: 'dataview',
				trackOver: true,
				cls: 'feed-list',
				itemSelector: '.library-list-item',
				overItemCls: 'library-list-item-hover',
				tpl: '<tpl for="."><div class="library-list-item">{name}</div></tpl>',
				listeners: {
					selectionchange: this.onSelectionChange,
					scope: this
				}
			}],

			dockedItems: [{
				xtype: 'toolbar',
				items: [{
					iconCls: 'library-add',
					text: 'Add Collection',
					action: 'add'
				}]
			}]
		});
		this.callParent(arguments);
		// Workaround for bug that ignores previous definition
		this.animCollapse = false;
	},

	onSelectionChange: function(selmodel, selection) {
		var selected = selection[0],
			button = this.down('button[action=remove]');
		if (selected) {
			button.enable();
		}
		else {
			button.disable();
		}
	}
});
