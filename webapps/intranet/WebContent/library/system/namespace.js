var Namespace =
{
    reg : function(_Name)
    {
        var chk = false;
        var cob = "";
        var spc = _Name.split(".");
        for(var i = 0; i<spc.length; i++)
        {
            if(cob!=""){
                cob+=".";
            }
            cob+=spc[i];
            chk = this.exists(cob);
            if(!chk){
                this.create(cob);
            }
        }
        if(chk){
            throw "Namespace: " + _Name + " is already defined.";
        }
    },

    create : function(_Src)
    {
        eval("window." + _Src + " = new Object();");
    },

    exists : function(_Src)
    {
        eval("var NE = false; try{if(" + _Src + "){NE = true;}else{NE = false;}}catch(err){NE=false;}");
        return NE;
    }
};

Namespace.reg('Ajax');
Namespace.reg('System');
