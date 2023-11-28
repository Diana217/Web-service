using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WebApplication.Classes;
using System.Configuration;

namespace WebApplication
{
    public class ApplicationContext : DbContext
    {
        public DbSet<Column> Columns { get; set; }
        public DbSet<Database> Databases { get; set; } 
        public DbSet<Row> Rows { get; set; } 
        public DbSet<Table> Tables { get; set; }
        public DbSet<Classes.Type> Types { get; set; } 

        public ApplicationContext() { }
        public ApplicationContext(DbContextOptions<ApplicationContext> options)
            : base(options)
        {
            Database.EnsureCreated();
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                string connStr = ConfigurationManager.ConnectionStrings["DefaultConnection"].ConnectionString;
                optionsBuilder.UseSqlServer(connStr);
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {

        }
    }
}