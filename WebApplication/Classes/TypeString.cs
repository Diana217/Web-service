using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication.Classes
{
    public class TypeString : Type
    {
        public new bool Validate(string value)
        {
            return true;
        }
    }
}