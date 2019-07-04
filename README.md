# Unofficial Java client for public ZenHub API

Implements the read-only API of the public [ZenHub API](https://github.com/ZenHubIO/API). Pull requests welcome!


## Installation


1) Clone the repository and add the artifacts to your local Maven cache:
```
git clone https://github.com/jgwest/zenhub-api-java-client
cd zenhub-api-java-client
mvn install -DskipTests
```

2) Add the client dependency into your Maven pom.xml
```
<dependencies>
	<dependency>
		<groupId>zenhub-api-java-client</groupId>
		<artifactId>zenhub-api-java-client</artifactId>
		<version>1.0.0</version>
	</dependency>
  ( ... )
</dependencies>
```


## Usage

The exposed client services correspond to resources available through the [ZenHub API](https://github.com/ZenHubIO/API).

These are:
- Boards - See `BoardService.java`
- Dependencies - See `DependenciesService.java`
- Epic / Epics - See `EpicsService.java`
- Issue Data / Issue Events - See `IssuesService.java`

Example usage:
```java
// Generate a token in the API Tokens section of your ZenHub Dashboard 
// (https://app.zenhub.com/dashboard/tokens)
String yourZenHubApiKey = "/* your api token */"; 
ZenHubClient zhClient = new ZenHubClient("https://api.zenhub.io", yourZenHubApiKey);

IssuesService issuesService = new IssuesService(zhClient);

long repositoryId = /* github repository id number*/;
int issueNumber = /* issue number */;

ApiResponse<GetIssueDataResponseJson> response = issuesService.getIssueData(repositoryId, issueNumber);

GetIssueDataResponseJson json = response.getResponse();

```

See [ZHTest class](https://github.com/jgwest/zenhub-api-java-client/blob/master/tests/com/zhapi/tests/ZHTest.java) for sample API usage. 


