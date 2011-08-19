dojo.provide("encuestame.org.core.shared.utils.FoldersActions");

dojo.require("dojo.dnd.Source");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.InlineEditBox");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.FolderOperations');


dojo.declare(
    "encuestame.org.core.shared.utils.FoldersActions",
    [encuestame.org.core.shared.utils.FolderOperations],{

      templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/foldersAction.html"),

      /*
       * enable templates.
       */
      widgetsInTemplate: true,

      /*
       *
       */
      _folderSourceWidget : null,

      /*
       * post create.
       */
      postCreate : function() {
          this.inherited(arguments);
          this._loadFolders();
          dojo.connect(this._new, "onclick", this, this._addNewFolder);
      },

      /*
       * add new folder.
       */
      _addNewFolder : function(event){
          dojo.stopEvent(event);
          var node = this._createFolder({folderId: null, name : "Add new name."});
          this._folders.appendChild(node.domNode);
      },

      /*
       * load folders.
       */
      _loadFolders : function() {
          this.getAction("list");
          var load = function(data){
              dojo.empty(this._folders);
              dojo.forEach(
                      data.success.folders,
                      dojo.hitch(this, function(data, index) {
                          var node = this._createFolder(data);
                          this._folders.appendChild(node.domNode);
              }));
          };
          var params = {
              max : this.max,
              start : this.start
              };
          this._callFolderService(load, params, this.getAction("list"), false);
      },

      /*
       *
       */
      _createFolder : function(data) {
          var folder = new encuestame.org.core.shared.utils.FoldersItemAction(
                  {folderId: data.id, name : data.name, folderParentWidget: this});
          return folder;
      }
});

/**
 *
 */
dojo.declare(
        "encuestame.org.core.shared.utils.FoldersItemAction",
        [encuestame.org.core.shared.utils.FolderOperations],{

        templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/foldersItemAction.html"),

        widgetsInTemplate: true,

        name : "",

        folderParentWidget: null,

        /*
         * enable drop support. _getContextUrlService
         */
        dropSupport : true,

        folderId : null,

        _accept : ["tweetpoll", "poll", "survey"],

        _foldersourceWidget : null,

        /*
         * post create cycle.
         */
        postCreate : function(){
            if (this.dropSupport) {
                this._folderSourceWidget  = new dojo.dnd.Target(this._folder, {
                    accept: this._accept
                    });
                    dojo.connect(this._folderSourceWidget, "onDndDrop", dojo.hitch(this, this.onDndDropFolder));
               };
               var name = dijit.byId(this._name);
               if (name != null) {
                   console.debug("inline on change", name);
                   /*
                    * TODO: on change event issues, review.
                    */
                   name.onChange = dojo.hitch(this, function() {
                       if (this.folderId == null) {
                           this.folderId = this._create(name.get('value'));
                       } else {
                           this._update(name.get('value'));
                       }
                   });
                   console.debug("inline on change 2", name);
               } else {
                   console.error("inline error");
               }
               console.debug("widget inline", name);
        },

        /*
         * add folder.
         */
        _create : function(name) {
            console.debug("updated name to;", name);
            var id = null;
            var load = dojo.hitch(this, function(data){
                console.debug("data", data);
                console.info("updated name");
            });
            var params = {
                name : name
                };
            this._callFolderService(load, params, this.getAction("create"));
            return id;
        },

        /*
         * update folder.
         */
        _update : function(name) {
            console.debug("updated name to", name);
            var load = dojo.hitch(this, function(data){
                console.debug("data", data);
                console.info("updated name");
            });
            var params = {
                folderId :this.folderId,
                folderName : name
                };
            this._callFolderService(load, params, this.getAction("update"));
        },

        /*
         * add item.
         */
        _addItem : function(id) {
            var load = function(data){
                console.debug("data", data);
                console.info("Item Added");
            };
            var params = {
                folderId :this.folderId,
                itemId : id
                };
           this._callFolderService(load, params, this.getAction("move"));
        },

        /*
         * on drop on folder.
         */
        onDndDropFolder : function(source, nodes, copy, target) {
                dojo.forEach(dojo.query(".dojoDndItemSelected"), function(item){
                    dojo.removeClass(item, "dojoDndItemSelected");
                });
                dojo.forEach(dojo.query(".dojoDndItemAnchor"), function(item){
                    dojo.removeClass(item, "dojoDndItemAnchor");
                });
                if(dojo.dnd.manager().target !== this._folderSourceWidget){
                    return;
                }
                if(dojo.dnd.manager().target == dojo.dnd.manager().source){
                    console.debug("same");
                } else {
                    dojo.forEach(this._folderSourceWidget.getSelectedNodes(), dojo.hitch(this, function(item) {
                        //console.debug("item", item);
                        var tweetPollId = item.getAttribute('tweetpollId');
                        var type = item.getAttribute('dndtype');
                        //console.debug("tweetpollId", tweetPollId);
                        //console.debug("type", type);
                        this._addItem(parseInt(tweetPollId));
                        dojo.destroy(item);
                    }));
                }
        }

});