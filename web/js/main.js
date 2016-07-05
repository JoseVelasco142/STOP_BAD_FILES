
var SBF = SBF || {};

SBF.prototype = {
    mixUpFiles: function(){
       $('#fileList').mixItUp({
            animation: {
                effects: 'scale translateY(-600px) translateZ(-600px) rotateX(-180deg)',
                duration: 1000,
                easing: 'cubic-bezier(0.175, 0.90, 0.32, 1.05)'
            },
            load: {
                page: 1
            },
            control: {
                enable: true,
                toggleFilterButtons: true,
                activeClass: 'on'
            },
            pagination: {
                limit: 6,
                generatePagers: true,
                loop: true,
                pagerClass: 'btn btn-default'
            },
            layout: {
                containerClass: 'list'
            }
        }); 
        $('.info').click(function(){
              SBF.prototype.getInfoFile($(this).attr('access-key'));
        });
        $('.download').click(function(){
              SBF.prototype.downloadFile($(this));
        });
        $('.delete').click(function(){
              SBF.prototype.deleteFile($(this).attr('access-key') );
        });
    },
    login: function(){
       $.ajax({
            method: "POST",
            url: "/stopBadFiles/MainHandler",
            async: false,
            data: {
                'op': 'auth',
                'out': false,
                'username': $("#username").val(),
                'password': $("#password").val()
            },
            beforeSend: function() {
               $('#sendLogin').button("loading");
            }
        }).done(function(response) {  
            if (response === '1')
                 $('#accessDenied').empty().append("Wrong username or password");
            else if (response === '2')
                $('#accessDenied').empty().append("There was a problem with your login");
            else 
                $('html').empty().prepend(response);
            
            $('#sendLogin').button("retry");
        });
    },
    logout: function(){
       $.ajax({
            method: "GET",
            url: "/stopBadFiles/MainHandler",
            async: false,
            data: { 'op': 'auth',
                    'out': true
                 }
        }).done(function(response) {  
            location.href="/stopBadFiles/";
        });
    },
    getFileList: function(){
         $.ajax({
            method: "POST",
            url: "/stopBadFiles/MainHandler",
            async: false,
            data: { 'op': 'getfiles' }
        }).done(function(files) {
            $('#fileList').empty();
            JSON.parse(files).forEach(function(file) {
                var mixFile =  
                    '<div class="col-xs-12 file mix state'+file.state+'" data-filename="'+file.filename+'" data-size="'+(file.size/1024).toFixed(1)+'" data-user="'+file.owner+'" data-date="'+file.datetime+'">'+
                        '<div class="col-xs-3">'+file.filename+'</div>' +
                        '<div class="col-xs-2">'+(file.size/1024).toFixed(1)+' KB</div>' +
                        '<div class="col-xs-2">'+file.owner+'</div>' +
                        '<div class="col-xs-3">'+file.datetime+'</div>' +
                        '<div class="col-xs-2">'+
                             '<a class="btn glyphicon glyphicon-eye-open info" access-key="'+file.UUID+'"></a>'+
                             '<a class="btn glyphicon glyphicon-download download" access-key="'+file.UUID+'"></a>'+
                             '<a class="btn glyphicon glyphicon-trash delete" access-key="'+file.UUID+'"></a>'+
                        '</div>' +
                    '</div>';
                $('#fileList').append(mixFile);  
            });
            SBF.prototype.mixUpFiles();
        });
    },
    getInfoFile: function(uuid){
          $.ajax({
            method: "POST",
            url: "/stopBadFiles/MainHandler",
            data: {
                'op': 'getInfo',
                'id': uuid
            }
        }).done(function(file) { 
            var info = JSON.parse(file);
            $('#viewUUID').html(info.UUID);
            $('#viewFilename').html(info.filename);
            $('#viewSize').html(info.size);
            $('#viewOwner').html(info.owner);
            $('#viewDate').html(info.datetime);
            $('#viewReport').html(info.report);
            $('#main').animate({
                width: "20%",
                opacity: 0.2
            }, 1500);
            $('#fileViewer').animate({
                width: "60%",
                opacity: 1
            }, 1500);
        });
    },
    downloadFile: function(file){
        var uuid = file.attr('access-key');
        var fileBlock = file.parent().parent(); 
        var notScanned = fileBlock.hasClass("state0");
        var infected = fileBlock.hasClass("state2");
        if(notScanned) {
            if(confirm("This file still not been scanned. Are you sure you download it?"))
               window.location.href = "/stopBadFiles/MainHandler?op=download&id="+uuid; 
        } else if(infected) {
            if(confirm("This file contains malicious content. Are you sure you download it?"))
               window.location.href = "/stopBadFiles/MainHandler?op=download&id="+uuid; 
        } else {
            window.location.href = "/stopBadFiles/MainHandler?op=download&id="+uuid;
        }
    },
    deleteFile: function(uuid){
         $.ajax({
            method: "POST",
            url: "/stopBadFiles/MainHandler",
            async: false,
            data: {
                'op': 'delete',
                'id': uuid
            }
        }).done(function(response) { 
            $('#fileList').mixItUp('destroy');
            SBF.prototype.getFileList();
        });
    }
};