using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication.Classes
{
    public class TypeEnum : Type
    {
        public new bool Validate(string value)
        {
            string[] buf = value?.Split(',');
            if (buf != null && buf.Length == 0)
                return false;
            return true;
        }
    }
}