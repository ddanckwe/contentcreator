Content Creator
===============

The Content Creator is a command-line tool you can use to create demo and test content in a CoreMedia CMS repository.


Usage
-----

Run the following command from within the bin directory to create content.

    cm createcontent -u <user> [-p <password>] -t <type>
      [-f <folder>] [-c <copies>] [-s <source folder>]
      [other options]

Available options:
*    -c,--copies \<arg\> 	Number of copies
*    -d,--domain \<domain name\> 	domain for login (default=\<builtin\>)
*    -f,--folder \<arg\> 	Base folder for the created content
*    -p,--password \<password\> 	password for login
*    -t,--type \<arg\> 	Content type
*    -u,--user \<user name\> 	user for login (required)
*    -url \<ior url\> 	url to connect to
*    -v,--verbose 	enables verbose output
*    -s,--source 	source folder of the original content that shall be created


Configuration
-------------

You can configure where and how your content should be created via Spring beans in
    config/createcontent/spring/createcontent.xml.

Content creation is configured via so called Content Creation Strategies and Property Populators.
Mainly a Content Creation Strategy defines where and what type of content should be created and uses a number of
Property Populators to fill the properties of the created content.


Maven
-----

If you want to include the Content Creator contribution in your project you can use the the following Maven artifacts.

<table>
  <thead>
    <tr>
      <th>Group ID</th>
      <th>Artifact ID</th>
      <th>Type</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>com.coremedia.contribution.contentcreator</td>
      <td>contentcreator-application</td>
      <td>coremedia-application</td>
    </tr>
    <tr>
      <td>com.coremedia.contribution.contentcreator</td>
      <td>contentcreator-lib</td>
      <td>jar</td>
    </tr>
    <tr>
      <td>com.coremedia.contribution.contentcreator</td>
      <td>contentcreator</td>
      <td>pom</td>
    </tr>
  </tbody>
</table>


Documentation
-------------

Please refer to the wiki pages for detailed information.


Contact
-------

If you need help or want to give feedback, you can send a mail to the Content Creator mailing list:
contentcreator@contributions.coremedia.com