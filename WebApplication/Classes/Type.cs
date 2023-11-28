using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication.Classes
{
    public class Type
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public bool Validate(string value) => true;
    }
}