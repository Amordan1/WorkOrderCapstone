<%-- 
    Document   : nav
    Created on : Mar 22, 2022, 1:55:33 PM
    Author     : nguye
--%>
<style>

    nav		{
        background-color:	#333;
        font-weight:		bold;
        text-align:		center;
        padding:		14px 16px;
        text-align: left;
        font-weight: bold;
        font-size: 25px;
        color: white;
        text-decoration: none;
    }

    nav ul	 	{
        list-style-type: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
    }

    nav li	 	{
        display: inline;
        text-align: center;
        padding: 14px 16px;
    }

    a    		{
        text-decoration: none;
    }

    a:link 		{
        color: lightseagreen;
    }

    a:visited 	{
        color: lightseagreen;
    }

    a:hover 	{
        color: darksalmon;
    }

</style>

<nav style="text-align: center;">
    <a href="Private?action=adminProfile">Profile </a>| 
    <a href="Private?action=orders"> My Work Orders </a>|
    <a href="Private?action=adminAll"> All Work Orders </a>|
    <a href="Private?action=logout"> Logout</a> <!--    target-->

</nav>
