Ext.application({

	name: 'Archer',

	appFolder: '/assets/javascripts/webInterface/',

	// All the paths for custom classes
	paths: {
		'Ext.ux': '/assets/javascripts/webInterface/plugins'
	},

	// Define all the controllers that should initialize at boot up of your application
	//controllers: [
	//	'Articles',
	//	'Feeds'
	//],

	autoCreateViewport: false,

	launch: function() {
		this.viewport = Ext.create('Archer.view.Viewport', {
			width: '100%',
			height: '100%',
			renderTo: Ext.Element.get('app')
		});
	}

});

Ext.EventManager.onWindowResize(function(width, height){
	Archer.getApplication().viewport.doComponentLayout();
});